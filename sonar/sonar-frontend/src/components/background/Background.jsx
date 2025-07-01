import pic from "../../assets/images/best_background.png";
import React from "react";
import "./Background.scss"

export const Background = () => {
    return (
        <img src={pic} alt={"Background Image"} className="sonar-map_image"/>
    )
}