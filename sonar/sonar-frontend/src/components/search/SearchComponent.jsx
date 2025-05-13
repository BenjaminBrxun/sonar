import React, {useEffect, useState} from "react";
import {TextField} from "@mui/material";
import "@fontsource/roboto/800.css";
import {BaseComponent} from "../base/BaseComponent.jsx";
import "./SearchComponent.scss"

export function SearchComponent({sendDataToParent}) {

    const [query, setQuery] = useState("");

    useEffect(() => {
        const timeOutId = setTimeout(() => console.debug("Inserted search after delay: " + query), 500);
        return () => clearTimeout(timeOutId);
    }, [query]);

    return (
        <>
            <BaseComponent sendDataToParent={sendDataToParent}/>

            <div className="input-container">
                <TextField
                    id="outlined-basic"
                    variant="outlined"
                    fullWidth
                    label="Eventsuche"
                    onChange={(e) => {
                        setQuery(e.target.value)
                    }}
                />

                <p style={{color: 'black', fontWeight: 'bold', textAlign: 'center', fontFamily: 'Roboto'}}>Geht
                    natürlich noch nicht :)</p>
            </div>
        </>
    )

}