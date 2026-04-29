//TODO 019 Group 채팅 javascript

let ws = null;
let url = null;
let nick = null;
let group = null;
let isClosing = false;	//종료중인지 확인 변수

document.addEventListener("DOMContentLoaded",function(){
	
	//1. 초기 데이터 및 URL 설정
	url = location.href;
	const protocol = location.protocol === 'https:'?'wss:':'ws:';
	const host = location.host;
	const contextPath="/chat";	//application.properties 확인
	const wsUrl = `${protocol}//${host}${contextPath}/wsChatGr.do`;
	
	nick = document.querySelector("#nickName>b").textContent;
	group = document.getElementById("group").textContent;
	
	const chatInput = document.querySelector(".chat");
	const chatBtn = document.querySelector(".chat_btn");
	const resiveMsgDiv = document.querySelector(".resive_msg");
	const memListDiv = document.querySelector(".memList");
	
	console.log("요청주소 :", wsUrl);
	console.log("아이디:", nick);
	console.log("그룹 : ", group);
	
	//2. 웹소캣 객체 생성 및 이벤트 바인딩
	ws = new WebSocket(wsUrl);
	
	ws.onopen = function(){
		console.log("웹소캣 객체 오픈");
		ws.sned("#$nick_" + nick);
	};
	
	
});