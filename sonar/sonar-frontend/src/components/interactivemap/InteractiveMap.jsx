import map from "../../assets/map_dark.png";
import React from "react";
import "./InteractiveMap.scss"

export const InteractiveMap = () => {
    return (
        <img src={map} alt={"Interactive Map"} className="map"/>
    )
}