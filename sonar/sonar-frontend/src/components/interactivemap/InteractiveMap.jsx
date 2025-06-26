import map from "../../assets/images/map_dark.png";
import React from "react";
import "./InteractiveMap.scss"

export const InteractiveMap = ({ isVisible = true}) => {
    return (
        <div className={`interactiveMap-container ${isVisible ? "visible" : "hidden"}`}>
            <img src={map} alt={"Interactive Map"} className="sonar-map_image"/>
            <div className="circle c1" title="Ort A" onClick={() => alert('Ort A!')}></div>
            <div className="circle c2" title="Ort B" onClick={() => alert('Ort B!')}></div>
            <div className="circle c3" title="Ort C" onClick={() => alert('Ort C!')}></div>
            <div className="circle c4" title="Ort D" onClick={() => alert('Ort D!')}></div>
            <div className="circle c5" title="Ort E" onClick={() => alert('Ort E!')}></div>
        </div>
    );
};