import React, { useState } from "react";

function Weather() {

    const [city, setCity] = useState("");
    const [weather, setWeather] = useState(null);

    const getWeather = async () => {

        if(city === ""){
            alert("도시를 입력하세요");
            return;
        }

        const res = await fetch(
            `http://localhost:8080/api/weather?city=${city}`
        );

        const data = await res.json();

        setWeather(data);
    };

    return (
        <div style={{textAlign:"center", marginTop:"100px"}}>

            <h1>🌤 날씨 조회</h1>

            <input
                type="text"
                placeholder="도시 입력 (ex: Seoul)"
                value={city}
                onChange={(e)=>setCity(e.target.value)}
            />

            <button onClick={getWeather}>조회</button>

            {weather && (
                <div style={{marginTop:"40px"}}>

                    <h2>{weather.city}</h2>

                    <img
                        src={`https://openweathermap.org/img/wn/${weather.icon}@2x.png`}
                    />

                    <h3>{weather.temp} °C</h3>

                    <p>{weather.description}</p>

                </div>
            )}

        </div>
    );
}

export default Weather;
