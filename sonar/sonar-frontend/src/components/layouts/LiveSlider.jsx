import React, {useState} from 'react';

export function LiveSlider({name, text}) {
    const [value, setValue] = useState(12);
    const handleChange = (event) => {
        setValue(event.target.value);
    }

    return (
        <div style={{display: 'flex', alignItems: 'center', gap: '1rem', fontStyle: 'bold'}}>
            <input type="range" min="0" max="18" step="1" value={value} onChange={handleChange} id={name}/>
            <span>{value} {text}</span>
        </div>
    )
}