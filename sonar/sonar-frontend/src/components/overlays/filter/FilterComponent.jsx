import React from "react";
import "./FilterComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";

export function FilterComponent({sendDataToParent}) {
    return (
        <>
            <BaseComponent sendDataToParent={sendDataToParent}/>
            <div className="placeholder-container">
                <p className="placeholder">Hier kommt eine Filterfunktion hin <br></br>:)</p>
            </div>
        </>
    )
}