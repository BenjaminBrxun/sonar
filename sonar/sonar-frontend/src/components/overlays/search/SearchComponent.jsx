import React, {useState} from "react";
import "@fontsource/roboto/800.css";
import "./SearchComponent.scss"
import {navigate} from "vike/client/router";
import {BaseComponent} from "../../base/BaseComponent.jsx";

export function SearchComponent({onCloseClick}) {

    const [searchTerm, setSearchTerm] = useState("");

    async function handleSubmit(e) {
        e.preventDefault();

        const queryParams = new URLSearchParams();

        if (searchTerm) {
            queryParams.append("name", searchTerm);
        }


        const filterUrl = `http://localhost:8081/api/v1/events/search?${queryParams.toString()}`;
        await navigate(`/list?link=${encodeURIComponent(filterUrl)}`);
    }

    return (
        <>
            <div className="search-overlay">
                <div className="search-header">
                    <h1>Eventsuche</h1>
                    <BaseComponent sendDataToParent={onCloseClick}/>
                </div>
                <div className="input-container">
                    <form onSubmit={handleSubmit}>
                        <input className="search-input" placeholder="Eventname" minLength={3} required={true} onChange={(e) => setSearchTerm(e.target.value)}/>
                        <button className="search-button" type="submit">Suchen</button>
                    </form>
                </div>
            </div>
        </>
    )

}