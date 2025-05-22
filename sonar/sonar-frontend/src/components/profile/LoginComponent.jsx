import React from "react";
import {BaseComponent} from "../base/BaseComponent.jsx";
import "./LoginComponent.scss"


export function LoginComponent({sendDataToParent}) {
    return (
        <>
            <BaseComponent sendDataToParent={sendDataToParent}/>
            <div className="placeholder-container">
                <p className="placeholder">Hier kommt eine Loginfunktion hin <br></br>:)</p>
            </div>
        </>
    )
}