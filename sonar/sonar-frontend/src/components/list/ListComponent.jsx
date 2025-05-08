import CancelIcon from '@mui/icons-material/Cancel';
import React from "react";
import {EventComponent} from "../../model/EventComponent.jsx";


export function ListComponent({sendDataToParent}) {

    function handleClick() {
        sendDataToParent(true);
    }

    return (
        <div style={{
            width: '412px',
            height: '915px',
            zIndex: 100
        }}>
            <div style={{
                display: 'flex',
                flexDirection: 'row-reverse',
            }}>
                <CancelIcon
                    sx={{color: '#FABB22', fontSize: 45, margin: '15px', position: 'absolute', zIndex: 100}}
                    onClick={() => {
                        handleClick();
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
                flexDirection: 'row',
                height: '100vh',
                alignItems: 'center',
                position: 'absolute',
                top: '0',
                justifyContent: 'center',
            }}>
                <div style={{
                    fontSize: 20,
                    zIndex: 10,
                    color: 'black',
                    fontWeight: 'bold',
                    maxHeight: '80%',
                    overflowY: 'auto',
                    // maxWidth: '90%',
                    width: '100%',
                    alignItems: 'center'

                }}>
                    <EventComponent sendDataToParent={sendDataToParent}/>
                    <EventComponent sendDataToParent={sendDataToParent}/>
                    <EventComponent sendDataToParent={sendDataToParent}/>
                    <EventComponent sendDataToParent={sendDataToParent}/>
                    <EventComponent sendDataToParent={sendDataToParent}/>
                </div>
            </div>
        </div>
    )

}