import {LoginComponent} from "../../../components/overlays/profile/LoginComponent.jsx";
import {useCookies} from "react-cookie";
import {navigate} from "vike/client/router";


export default function Test() {
    const [cookies, setCookie, removeCookie] = useCookies(['email']);
    async function logoutToList() {
        await navigate('/list');
    }
    if(cookies.email !== undefined) {
        removeCookie("email");
        logoutToList();
    }
    return <>
        <LoginComponent/>
    </>;
}