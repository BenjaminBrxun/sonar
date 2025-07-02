import "./BaseComponent.scss"
import CancelIcon from "@mui/icons-material/Cancel";
import React from "react";
import {navigate} from "vike/client/router";

export function BaseComponent({sendDataToParent}) {


    async function goToIndex() {
        await navigate('/list');
    }

    return (
            <div className="icon-container">
                <CancelIcon className="cancel-icon"
                            onClick={() => {
                                goToIndex();
                            }}/>
                <div className='icon-background'/>
            </div>
    )
}
