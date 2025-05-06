import map from "../assets/map_dark.png";
import React from "react";

export const InteractiveMap = () => {
    return (
        <img src={map} alt={"Interactive Map"} style={{width: '100vw', height: '100vh'}}/>
    )
}