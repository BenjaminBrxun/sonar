import pic from "../../assets/images/sonar-background-new.png";
import React from "react";
import "./Background.scss"

export const Background = () => {
    return (
        <img src={pic} alt={"Background Image"} className="sonar-map_image"/>
    )
}