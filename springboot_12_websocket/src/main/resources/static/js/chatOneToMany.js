//TODO 011 1:N 채팅 동작 javascript

let ws = null;	//웹소캣객체
let nick = null;	//대화명

//Document(DOM요소)가 모두 로드된 후 실행
document.addEventListener("DOMContentLoaded", function(){
	
	const resiveDiv = document.getElementById("resive_msg");
	const nickInput = document.getElementById("nickName");
	const joinBtn = document.getElementById("join_room");
	
	const chatDiv = document.getElementById("chat_div");
	const chatInput = document.getElementById("chat");
	const chatBtn = document.getElementById("chat_btn");
	
	nickInput.focus();
	
	//1) 대화명을 입력
	joinBtn.addEventListener("click", function(){
		if(nickInput.value.trim() === ""){
			alert("참여 이름은 필수입니다");
			nickInput.focus();
			return;
		}
		
		nick = nickInput.value;
		console.log("참여 이름 : ", nick);
		
		//UI전환시켜준다(대화명 입력란 삭제, 채팅창 표시)
		resiveDiv.innerHTML="";
		chatDiv.style.display="block";
		chatInput.focus();
		
		//웹소캣 주소 생성(Context Path 생성)
		const protocol = location.protocol === 'https:'?'wss:':'ws:';
		const host = location.host;
		const contextPath="/chat";	//application.properties 확인
		const wsUrl = `${protocol}//${host}${contextPath}/wsChat.do`;
		
		console.log("접속 URL : ", wsUrl);
		ws = new WebSocket(wsUrl);	//서버의 afterConnectionClosed 처리
		
		//웹소캣 이벤트 바인딩
		ws.onopen = function(){
			console.log("연결성공");
			ws.send("#$nick_"+nick);	//서버의 WebSocket의 handleTextMessage send() 처리
		};
		
		ws.onmessage = function(event){	//서버로부터 handleTextMessage 전달 : BrodeCasting
			console.log(event.data.startsWith("[ALERT]"));
			if(event.data.startsWith("[ALERT]")){
				const alertMsg = event.data.replace("[ALERT]","");
				alert(alertMsg);
			}
			
			const msgNode = document.createElement("div");
			msgNode.innerHTML = event.data;
			resiveDiv.appendChild(msgNode);
			resiveDiv.scrollTop = resiveDiv.scrollHeight;
		};
		
		ws.onclose = function(){
			alert("웹소캣 서버와 연결이 종료되었습니다");
		};
		
		ws.onerror = function(err){
			console.log("웹소캣 에러 : ", err);
		};
		
		//2) [전송] 버튼 이벤트
		chatBtn.addEventListener("click", function(){
			const message = chatInput.value;
			if(message.trim() == ""){
				alert("대화 내용을 입력해 주세요");
				return;
			}
			//readyState 연결 => AJAX
			//0(연결중), 1(연결완료), 2(닫는중), 3(닫힘)
			if(ws && ws.readyState === WebSocket.OPEN){
				ws.send(`[${nick}] ${message}`)
				chatInput.value='';
				chatInput.focus();
			}
		});	//chatBtn click 끝
		
		//3) 엔터키 이벤트
		nickInput.addEventListener("keypress", (e) => {if(e.key === "Enter") joinBtn.click()});
		chatInput.addEventListener("keypress", (e) => {if(e.key === "Enter") chatBtn.click()});
		
		
	});
	
	//4) 연결 종료 함수를 ws.onclose()와 연결
	function disconnection(){
		if(ws){
			ws.close();
			ws=null;
		}
		location.reload();
	}
	
	//5) 브라우저가 강제로 닫힐 때
	window.onbeforeunload = function(){
		if(ws){
			ws.close();
		}
	}
	
	
	
	
	
	
});