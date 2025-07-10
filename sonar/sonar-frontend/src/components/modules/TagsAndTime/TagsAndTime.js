function restrictedToString(restricted) {
    if (restricted) {
        return "Mit Anmeldung";
    } else {
        return "Ohne Anmeldung";
    }
}

function costsWithCurrency(costs) {
    return costs + " €";
}

function dateToDateSpan(date) {
    let dateFormat = new Date(date);
    let milliseconds = dateFormat.getTime();
    let today = new Date();
    let difference = today.getTime() - milliseconds;

    if (difference > 0) {
        return ["vergangen", "_past"]
    }

    if (difference <= 86400000 && difference >= -86400000) {
        return ["heute", "_today"]
    }

    if (difference < 0 && difference >= -259200000) {
        return ["in kürze", "_next"]
    }

    if (difference < -259200000) {
        return ["zukünftig", "_soon"]
    }
}

function format_date_to_text(eventdate) {
    console.log("format_date_to_text", eventdate);
    const year = eventdate.substring(0, 4);
    const month = eventdate.substring(5, 7);
    const day = eventdate.substring(8, 10);
    const hour = eventdate.substring(11, 13);
    const minute = eventdate.substring(14, 16);
    // const second = eventdate.substring(17, 19);
    const erg = day + "." + month + "." + year + " " + hour + ":" + minute + "Uhr";
    console.log("LOGGGG>>"+erg)
    return erg;
}

export {restrictedToString, costsWithCurrency, dateToDateSpan, format_date_to_text}