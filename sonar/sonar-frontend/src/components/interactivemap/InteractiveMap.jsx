import map from "../../assets/images/map_dark.png";
import React from "react";
import "./InteractiveMap.scss"

export const InteractiveMap = () => {
    return (
        <div className="interactiveMap-container">
            <img src={map} alt={"Interactive Map"} className="sonar-map_image" useMap="#workmap"/>
            <map name="workmap">
                <area shape="circle" coords="200,200,50" alt="Computer" href="localhost:8080"/>
            </map>
        </div>
    )
}