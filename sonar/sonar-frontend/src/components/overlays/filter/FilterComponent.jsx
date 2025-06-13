import React from "react";
import "./FilterComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";
import {HighlightGroup, MultiSelectHighlightGroup} from "../../layouts/HighlightGroup.jsx"
//import 'bootstrap/dist/css/bootstrap.min.css';


export function FilterComponent({onCloseClick}) {
    const [selected, setSelected] = React.useState(null);
    const [multiSelected, setMultiSelected] = React.useState([]);
    const prices = ['Kostenlos', '€', '€€', '€€€']
    const restricted = ['ohne Anmeldung', 'mit Anmeldung']
    const categories = ['Sport', 'Museum', 'Musik', 'Fest', 'Gaming', 'Natur', 'Kino', 'Theater', 'Workshop', 'Computer', 'Ganze Familie', 'Tiere']

    return (
        <div className="filter-overlay">
            <h1>Filteroptionen</h1>

            <form>
                <fieldset>
                    <legend>
                        Zeitraum
                    </legend>
                    <p>
                        <input type="date"/>
                        <input type="date"/>
                    </p>
                </fieldset>
                <fieldset>
                    <legend>Preisspanne</legend>
                    <p>
                        <HighlightGroup
                            options={prices}
                            selected={selected}
                            setSelected={setSelected}
                        />
                    </p>
                </fieldset>
                <fieldset>
                    <legend>Teilnahmebeschränkung</legend>
                    <p>
                        <HighlightGroup
                            options={restricted}
                            selected={selected}
                            setSelected={setSelected}
                        />
                    </p>
                </fieldset>
                <fieldset>
                    <legend>Mein Alter</legend>
                    <p>
                        <label>0-18</label>
                        <input type="range" min="0" max="18" step="1"/>
                    </p>
                </fieldset>
                <fieldset>
                    <legend>Kategorien</legend>
                    <p>
                        <MultiSelectHighlightGroup
                            options={categories}
                            selected={multiSelected}
                            setSelected={setMultiSelected}
                        />
                    </p>
                </fieldset>
            </form>
            <BaseComponent sendDataToParent={onCloseClick}/>

        </div>
    )
}