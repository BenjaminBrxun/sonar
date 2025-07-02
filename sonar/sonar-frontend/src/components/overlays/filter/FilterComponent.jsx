import React, {useState} from "react";
import "./FilterComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";
import {HighlightGroup, MultiSelectHighlightGroup} from "../../layouts/HighlightGroup.jsx"
import {LiveSlider} from "../../layouts/LiveSlider.jsx"
import {navigate} from "vike/client/router";

//import 'bootstrap/dist/css/bootstrap.min.css';


export function FilterComponent({onCloseClick}) {
    const [priceSelected, setPriceSelected] = React.useState(null);
    const [restrictedSelected, setRestrictedSelected] = React.useState(null);
    const [multiSelected, setMultiSelected] = React.useState([]);
    const [dateFrom, setDateFrom] = React.useState("");
    const [dateTo, setDateTo] = React.useState("");
    const [minAgeSelected, setMinAgeSelected] = React.useState("");
    const prices = ['Kostenlos', '€', '€€', '€€€']
    const restricted = ['ohne Anmeldung', 'mit Anmeldung']
    const categories = ['Sport', 'Museum', 'Musik', 'Fest', 'Gaming', 'Natur', 'Kino', 'Theater', 'Workshop', 'Computer', 'Ganze Familie', 'Tiere']
    const categoryNameToId = {
        "Sport": 1,
        "Museum": 2,
        "Musik": 3,
        "Fest": 4,
        "Gaming": 5,
        "Natur": 6,
        "Kino": 7,
        "Theater": 8,
        "Workshop": 9,
        "Computer": 10,
        "Ganze Familie": 11,
        "Tiere": 12
    };
    const [searchTerm, setSearchTerm] = useState("");

    function priceToFloat(price) {
        switch (price) {
            case 'Kostenlos':
                return 0
            case '€':
                return 5.0
            case '€€':
                return 10.0
            case '€€€':
                return 9999.99
        }
    }

    async function handleSubmit(e) {
        e.preventDefault();

        const queryParams = new URLSearchParams();
        if (searchTerm) {
            queryParams.append("name", searchTerm);
        }
        multiSelected.forEach(category => {
            const id = categoryNameToId[category];
            queryParams.append('categories', id);
        });

        if(dateFrom) {
            const startTimestamp = new Date(dateFrom).getTime();
            queryParams.append("startDate", startTimestamp);
        }

        if(dateTo) {
            const endTimestamp = new Date(dateTo).getTime();
            queryParams.append("endDate", endTimestamp);
        }

        if(priceSelected) {
            queryParams.append("price", priceToFloat(priceSelected));
        }

        if(restrictedSelected) {
            queryParams.append("restricted", (restrictedSelected === "mit Anmeldung"));
        }

        if(minAgeSelected) {
            queryParams.append("minAge", minAgeSelected);
        }

        const filterUrl = `http://localhost:8081/api/v1/events/filter?${queryParams.toString()}`;
        await navigate(`/list?link=${encodeURIComponent(filterUrl)}`);

    }

    return (
        <div className="filter-overlay">
            <div className="filter-header">
                <label>Filteroptionen</label>
                <BaseComponent sendDataToParent={onCloseClick}/>
            </div>
            <div className="form-container">
            <form onSubmit={handleSubmit}>
                <div className="filter-body">
                    <fieldset>
                        <legend>Suchbegriff</legend>
                        <input className="search-input" placeholder="Eventname" minLength={3} required={true} onChange={(e) => setSearchTerm(e.target.value)}/>
                    </fieldset>
                <fieldset>
                    <legend>
                        Zeitraum
                    </legend>
                    <div>
                        <span><label>Von <input type="date" value={dateFrom} onChange={e => setDateFrom(e.target.value)}/></label></span>
                        <span><label>   Bis <input type="date" value={dateTo} onChange={e => setDateTo(e.target.value)}/></label></span>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Preisspanne</legend>
                    <div>
                        <HighlightGroup
                            options={prices}
                            selected={priceSelected}
                            setSelected={setPriceSelected}
                        />
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Teilnahmebeschränkung</legend>
                    <div>
                        <HighlightGroup
                            options={restricted}
                            selected={restrictedSelected}
                            setSelected={setRestrictedSelected}
                        />
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Mein Alter</legend>
                    <LiveSlider name={"age"} text={" Jahre"} minAge={minAgeSelected} setMinAge={setMinAgeSelected}/>

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
                </div>
                <div className="filter-footer">
                    <fieldset>
                        <legend></legend>
                        <div>
                            <input type="reset" value="Filter zurücksetzen" className="reset-button"/>
                            <input type="submit" value="Filter anwenden" className="send-button"/>
                        </div>
                    </fieldset>
                </div>
            </form>
            </div>

        </div>
    )
}