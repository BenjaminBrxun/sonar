import map from "../../assets/images/map_dark.png";
import React from "react";
import "./InteractiveMap.scss"

export const InteractiveMap = () => {
    return (
        <div className="interactiveMap-container">
            <img src={map} alt={"Interactive Map"} className="sonar-map_image" useMap="#map"/>
            <map name="map">
                <area shape="circle" coords="215,495,15" alt="Computer" href="https://klpt.de/cdn/shop/files/Klappt._Logo_1500x1500_Zeichenflaeche_1.png?height=628&pad_color=f5f7f6&v=1659013043&width=1200"/>
            </map>
            <div className="circle pulse green c1"/>
        </div>
    );
};