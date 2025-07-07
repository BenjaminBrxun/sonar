import React from "react";
import {BaseComponent} from "../../base/BaseComponent.jsx";
import "./LoginComponent.scss"


export function LoginComponent({onCloseClick}) {

    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");

    async function handleSubmit(e) {

    }

    return (
        <div className="login-overlay">
            <div className="login-header">
                <label>Login</label>
                <BaseComponent sendDataToParent={onCloseClick}/>
            </div>
            <div className="login-container">
                <form onSubmit={handleSubmit}>
                    <div className="login-body">
                        <fieldset>
                            <legend>Login-Daten</legend>
                            <div className="credentials-container">
                                <div className="input-container">
                                    <label className="login-label" htmlFor="email-input">Email</label>
                                    <input id="email-input" type="email" value={email} required onChange={e => setEmail(e.target.value)}/>
                                </div>
                                <div className="input-container">
                                    <label className="login-label" htmlFor="password-input">Passwort</label>
                                    <input id="password-input" type="password" value={password} required
                                           onChange={e => setPassword(e.target.value)}/>
                                    <button className="forgot-password-button" type="submit">Passwort vergessen?</button>
                                </div>
                            </div>
                        </fieldset>
                        <div className="button-container">
                            <button className="login-button" type="submit">Login</button>
                        </div>
                    </div>
                </form>
            </div>
            <hr className="separator"/>
            <div className="button-container register-container">
                <p className="create-account-text">Du hast noch keinen Account?</p>
                <button className="register-button" type="button">Registrieren</button>
            </div>
        </div>

    )
}