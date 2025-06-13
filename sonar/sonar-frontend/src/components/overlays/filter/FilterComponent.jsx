import React from "react";
import "./FilterComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";
import {HighlightGroup, MultiSelectHighlightGroup} from "../../layouts/HighlightGroup.jsx"
import {LiveSlider} from "../../layouts/LiveSlider.jsx"
//import 'bootstrap/dist/css/bootstrap.min.css';


export function FilterComponent({onCloseClick}) {
    const [selected, setSelected] = React.useState(null);
    const [multiSelected, setMultiSelected] = React.useState([]);
    const prices = ['Kostenlos', '€', '€€', '€€€']
    const restricted = ['ohne Anmeldung', 'mit Anmeldung']
    const categories = ['Sport', 'Museum', 'Musik', 'Fest', 'Gaming', 'Natur', 'Kino', 'Theater', 'Workshop', 'Computer', 'Ganze Familie', 'Tiere']

    return (
        <div className="filter-overlay">
            <div className="filter-header">
                <h1>Filteroptionen</h1>
                <BaseComponent sendDataToParent={onCloseClick}/>
            </div>

            <form>
                <fieldset>
                    <legend>
                        Zeitraum
                    </legend>
                    <div>
                        <span><label>Von <input type="date"/></label></span>
                        <span><label>   Bis <input type="date"/></label></span>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Preisspanne</legend>
                    <div>
                        <HighlightGroup
                            options={prices}
                            selected={selected}
                            setSelected={setSelected}
                        />
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Teilnahmebeschränkung</legend>
                    <div>
                        <HighlightGroup
                            options={restricted}
                            selected={selected}
                            setSelected={setSelected}
                        />
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Mein Alter</legend>
                    <LiveSlider name={"age"} text={" Jahre"}/>

                </fieldset>
                <fieldset>
                    <legend>Kategorien</legend>
                    <div>
                        <MultiSelectHighlightGroup
                            options={categories}
                            selected={multiSelected}
                            setSelected={setMultiSelected}
                        />
                    </div>
                </fieldset>
                <fieldset>
                        <legend></legend>
                    <div>
                        <input type="reset" value="Filter zurücksetzen" className="send-button"/>
                        <input type="submit" value="Filter anwenden"  className="send-button"/>
                    </div>
                </fieldset>
            </form>


        </div>
    )
}