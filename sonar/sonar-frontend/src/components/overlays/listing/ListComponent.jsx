import React from "react";
import "./ListComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";


export function ListComponent({sendDataToParent}) {
    return (
        <>
            <BaseComponent sendDataToParent={sendDataToParent}/>
            <div className="placeholder-container">
                <p className="placeholder">Hier kommt eine Listenfunktion hin <br></br>:)</p>
            </div>
        </>
    )
}