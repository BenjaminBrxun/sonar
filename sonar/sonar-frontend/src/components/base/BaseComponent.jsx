import "./BaseComponent.scss"
import CancelIcon from "@mui/icons-material/Cancel";
import React from "react";

export function BaseComponent({sendDataToParent}) {

    function handleClick() {
        console.log("clicked")
        sendDataToParent(true);
    }

    return (
        <div id="base" className="component-container">
            <div className="icon-container">
                <CancelIcon className="cancel-icon"
                            onClick={() => {
                                handleClick();
                            }}/>
                <div className='icon-background'></div>
            </div>
            <div className="background"/>
        </div>
    )
}