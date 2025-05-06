import CancelIcon from '@mui/icons-material/Cancel';
import React, {useEffect, useState} from "react";
import {TextField} from "@mui/material";
import "@fontsource/roboto/800.css";

export function SearchComponent({sendDataToParent}) {
    const [data, setData] = useState(false);

    const [query, setQuery] = useState("");

    function handleClick() {
        sendDataToParent(true);
    }

    useEffect(() => {
            const timeOutId = setTimeout(() => console.log("Nach nem Delay: " + query), 500);
            return () => clearTimeout(timeOutId);
        },[query]);

    return (
        <div style={{
            width: '100vw',
            height: '100vh',
            zIndex: 100
        }}>
            <div style={{
                display: 'flex',
                flexDirection: 'row-reverse',
            }}>
                <CancelIcon
                    sx={{color: '#FABB22', fontSize: 45, margin: '15px', position: 'absolute', zIndex: 100}}
                    onClick={() => {
                        console.log('before assignment');
                        console.log('before handleClick()');
                        handleClick();
                        console.log('after assignment');
                    }}
                />
                <div className='cross-background' style={{
                    background: 'black',
                    borderRadius: '50px',
                    width: '30px',
                    height: '30px',
                    position: 'absolute',
                    top: '25px',
                    right: '25px',
                    zIndex: 9
                }}></div>
            </div>
            <div style={{
                background: 'white', width: '100%', height: '100%', opacity: '0.85', zIndex: 8,
            }}>
            </div>
            <div style={{
                display: 'flex',
                flexDirection: 'column',
                width: '100%',
                height: '100%',
                justifyContent: 'center',
                alignItems: 'center',
                position: 'absolute',
                top: '0',
            }}>
                <div className="search">
                    <TextField
                        id="outlined-basic"
                        variant="outlined"
                        fullWidth
                        label="Eventsuche"
                        onChange={(e) => {setQuery(e.target.value)}}
                    />
                    <p style={{color: 'black', fontWeight: 'bold', textAlign: 'center', fontFamily: 'Roboto'}}>Geht natürlich noch nicht :)</p>
                </div>
            </div>
        </div>
    )

}