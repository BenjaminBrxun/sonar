import "./BaseComponent.scss"
import CancelIcon from "@mui/icons-material/Cancel";
import React from "react";
import {navigate} from "vike/client/router";

export function BaseComponent({sendDataToParent}) {


    async function goToIndex() {
        const navigationPromise = navigate('/')
        console.log("The URL changed but the new page hasn't rendered yet.")
        await navigationPromise
        console.log('The new page has finished rendering.')
    }

    return (
        <div id="base" className="component-container">
            <div className="icon-container">
                <CancelIcon className="cancel-icon"
                            onClick={() => {
                                goToIndex();
                            }}/>
                <div className='icon-background'></div>
            </div>
            <div className="background"/>
        </div>
    )
}
