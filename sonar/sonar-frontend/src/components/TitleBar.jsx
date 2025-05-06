import {useState} from "react";
import logo from "../assets/logo.png";
import LanguageIcon from '@mui/icons-material/Language';
import {IconContext} from "react-icons";

export const TitleBar = () => {
    const [value, setValue] = useState(0)
    return (
        <div style={{width: '100%', position: 'absolute', top: '2%', display: 'flex', justifyContent: 'center'}}>
            <div style={{
                width: '95%',
                height: '56px',
                background: 'white',
                borderRadius: '20px',
                alignItems: 'center',
                display: 'flex'
            }}>
                <div style={{display: 'flex', alignItems: 'center'}}>
                    <img style={{width: '12%', margin: '10px'}} src={logo} alt={"Wappen der Stadt Herne"}/>
                    <p style={{color: 'black', fontWeight: 'bold', fontSize: 28}}>HERNEBUZZ</p>
                </div>
                <div style={{display: 'flex', justifyContent: 'space-around', alignItems: 'center', margin: '15px'}}>
                    <LanguageIcon sx={{color: 'black', fontSize: 40}}/>
                    <select
                        style={{background: 'white', color: 'black', border: 'none', fontWeight: 'bold', fontSize: 18}}>
                        <option value="DE">DE</option>
                        <option value="EN">EN</option>
                    </select>
                </div>
            </div>
        </div>
    )
}