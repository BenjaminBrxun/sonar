import map from "../../assets/images/map_dark.png";
import React from "react";
import "./InteractiveMap.scss"

export const InteractiveMap = ({ isVisible = true}) => {
    return (
        <div className={`interactiveMap-container ${isVisible ? "visible" : "hidden"}`}>
            <img src={map} alt={"Interactive Map"} className="sonar-map_image"/>
            <div className="circle c1 green pulse" title="Ort A" onClick={() => alert('Ort A!')}></div>
            <div className="circle c2 green pulse" title="Ort B" onClick={() => alert('Ort B!')}></div>
            <div className="circle c3 green pulse" title="Ort C" onClick={() => alert('Ort C!')}></div>
            <div className="circle c4 green pulse" title="Ort D" onClick={() => alert('Ort D!')}></div>
            <div className="circle c5 green pulse" title="Ort E" onClick={() => alert('Ort E!')}></div>
        </div>
    );
};