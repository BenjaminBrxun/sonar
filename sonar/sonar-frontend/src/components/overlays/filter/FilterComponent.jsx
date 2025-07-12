import React, {useEffect, useState} from "react";
import "./FilterComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";
import {HighlightGroup, MultiSelectHighlightGroup} from "../../layouts/HighlightGroup.jsx"
import {LiveSlider} from "../../layouts/LiveSlider.jsx"
import {navigate} from "vike/client/router";
import {EventFilter} from "./EventFilter.js";

//import 'bootstrap/dist/css/bootstrap.min.css';


export function FilterComponent({onCloseClick}) {

    const [eventFilter, setEventFilter] = useState(new EventFilter());

    // Selected and preset filter options
    const [priceSelected, setPriceSelected] = React.useState(eventFilter.priceSelected);
    const [restrictedSelected, setRestrictedSelected] = React.useState(eventFilter.restrictedSelected);
    const [selectedCategories, setSelectedCategories] = React.useState(eventFilter.selectedCategories);
    const [dateFrom, setDateFrom] = React.useState(eventFilter.dateFrom);
    const [dateTo, setDateTo] = React.useState(eventFilter.dateTo);
    const [minAgeSelected, setMinAgeSelected] = React.useState(eventFilter.minAgeSelected);
    const [searchTerm, setSearchTerm] = useState(eventFilter.searchTerm);

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const eventFilter = JSON.parse(localStorage.getItem("eventFilter"));
            if (eventFilter) {
                setEventFilterFromProps(eventFilter);
            }
        }
    }, []);

    function setEventFilterFromProps(eventFilter) {
        setEventFilter(eventFilter);
        setPriceSelected(eventFilter.priceSelected);
        setRestrictedSelected(eventFilter.restrictedSelected);
        setSelectedCategories(eventFilter.selectedCategories);
        setDateFrom(eventFilter.dateFrom);
        setDateTo(eventFilter.dateTo);
        setMinAgeSelected(eventFilter.minAgeSelected);
        setSearchTerm(eventFilter.searchTerm);
    }

    // Constants
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
        const eventFilter = new EventFilter();

        const queryParams = new URLSearchParams();
        if (searchTerm) {
            queryParams.append("name", searchTerm);
            eventFilter.searchTerm = searchTerm;
        }
        if (selectedCategories) {
            selectedCategories.forEach(category => {
                const id = categoryNameToId[category];
                queryParams.append('categories', id);
            });
            eventFilter.selectedCategories = selectedCategories;
        }

        if (dateFrom) {
            const startTimestamp = new Date(dateFrom + 'T00:00:00');
            queryParams.append("startDate", startTimestamp.toISOString());
            eventFilter.dateFrom = dateFrom;
        }

        if (dateTo) {
            const endTimestamp = new Date(dateTo + 'T23:59:59');
            queryParams.append("endDate", endTimestamp.toISOString());
            eventFilter.dateTo = dateTo;
        }

        if (priceSelected) {
            queryParams.append("price", priceToFloat(priceSelected).toString());
            eventFilter.priceSelected = priceSelected;
        }

        if (restrictedSelected) {
            queryParams.append("restricted", (restrictedSelected === "mit Anmeldung").toString());
            eventFilter.restrictedSelected = restrictedSelected;
        }

        if (minAgeSelected) {
            queryParams.append("minAge", minAgeSelected);
            eventFilter.minAgeSelected = minAgeSelected;
        }

        localStorage.setItem("eventFilter", JSON.stringify(eventFilter));
        const filterUrl = `http://localhost:8081/api/v1/events/filter?${queryParams.toString()}`;
        await navigate(`/list?link=${encodeURIComponent(filterUrl)}`);
    }

    const handleReset = (e) => {
        e.preventDefault();
        const eventFilter = new EventFilter();
        setEventFilterFromProps(eventFilter);

        if (typeof window !== 'undefined') {
            localStorage.removeItem("eventFilter");
        }
    };

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
                            <input className="search-input"
                                   placeholder="Eventname"
                                   minLength={3}
                                   value={searchTerm}
                                   onChange={(e) => setSearchTerm(e.target.value)}/>
                        </fieldset>
                        <fieldset>
                            <legend>
                                Zeitraum
                            </legend>
                            <div>
                                <span>
                                    <label>Von
                                    <input
                                        type="date"
                                        value={dateFrom}
                                        onChange={e => setDateFrom(e.target.value)}/>
                                </label>
                                </span>
                                <span>
                                    <label>Bis
                                        <input type="date"
                                               value={dateTo}
                                               onChange={e => setDateTo(e.target.value)}/>
                                    </label>
                                </span>
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
                            <LiveSlider name={"age"} text={" Jahre"} minAge={minAgeSelected}
                                        setMinAge={setMinAgeSelected}/>

                        </fieldset>
                        <fieldset>
                            <legend>Kategorien</legend>
                            <div>
                                <MultiSelectHighlightGroup
                                    options={categories}
                                    selected={selectedCategories}
                                    setSelected={setSelectedCategories}
                                />
                            </div>
                        </fieldset>
                    </div>
                    <div className="filter-footer">
                        <fieldset>
                            <legend></legend>
                            <div>
                                <input type="reset" onClick={handleReset} value="Filter zurücksetzen" className="reset-button"/>
                                <input type="submit" value="Filter anwenden" className="send-button"/>
                            </div>
                        </fieldset>
                    </div>
                </form>
            </div>

        </div>
    )

}