import React from "react";
import "./FavoritesComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";

export function FavoritesComponent({sendDataToParent}) {
    return (
        <>
            <BaseComponent sendDataToParent={sendDataToParent}/>
            <div className="placeholder-container">
                <p className="placeholder">Hier kommt eine Favoritenfunktion hin <br></br>:)</p>
            </div>
        </>
    )

}