import map from "../../assets/images/map_dark.png";
import React from "react";
import "./InteractiveMap.scss"

export const InteractiveMap = () => {
    return (
        <div className="interactiveMap-container">
            <div className="image-wrapper">
                <img src={map} alt={"Interactive Map"} className="sonar-map_image" useMap="#map" width="400" height="379"/>
                <map name="map">
                    <area shape="circle" coords="337,300,44" alt="Computer" href="coffe.htm"/>
                </map>
                <div className="circle pulse green" style={{top: "300px", left: "337px"}}/>
            </div>
        </div>
    );
};