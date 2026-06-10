import React from 'react'


// 서버사이트 렌더링 하기 때문에 useState useEffect 이 없다
const page =async() => {

  // Typescript 를 통해서 타입을 정의해 줘야한다
  interface User{
    id: number,
    name: string,
    email: string,
  }

  const generatedAt = new Date().toLocaleTimeString();

  // Fetch 데이터를 받아 온 후 동작됨 으로 await로 처리 한다
  // SSR (Server Site Rendering) - 동적 렌더링
  const res = await fetch('https://jsonplaceholder.typicode.com/users/',
              {cache :'no-store'}   
  );

  // SSG (Static Side Generation ) - 정적 렌더링
  // const res = await fetch('https://jsonplaceholder.typicode.com/users/',
  //             {next:{revalidate:10}}   
  // );
  
  const users:User[] = await res.json();

  return (
    // <div>UserPage</div>
    <>
      <h3>사용자들</h3>
      <div>현재시간 : {generatedAt}</div>
      <ul>
        {
          users.map(user => <li key={user.id}>{user.name}/{user.email}</li>)
        }
      </ul>
    </>
  )


}

export default page