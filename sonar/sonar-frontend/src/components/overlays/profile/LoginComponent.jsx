import React from "react";
import {BaseComponent} from "../../base/BaseComponent.jsx";
import "./LoginComponent.scss"
import {navigate} from "vike/client/router";


export function LoginComponent({onCloseClick}) {

    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");

    async function handleSubmit(e) {
        e.preventDefault();

        try {
            console.log(email + " " + password);
            const response = await fetch("http://localhost:8081/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            if (response.ok) {
                const data = await response.json();
                const token = data.token;
                console.log(response);
                localStorage.setItem("email", email);
                localStorage.setItem("token", token);

                alert("Login erfolgreich!");

                await navigate(`/list`);

                // TODO: Weiterleitung oder App-Zustand ändern
            } else if (response.status === 401) {
                alert("Falsche E-Mail oder Passwort");
            } else {
                alert("Fehler beim Login");
            }
        } catch (error) {
            console.error("Netzwerkfehler:", error);
            alert("Netzwerkfehler beim Login");
        }
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
                                    <la9bel className="login-label" htmlFor="password-input">Passwort</la9bel>
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