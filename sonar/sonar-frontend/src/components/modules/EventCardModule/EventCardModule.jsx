import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import ShareIcon from '@mui/icons-material/Share';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import "./EventCardModule.scss";

export default function EventCardModule({title, date, costs, image, restricted}) {

    // Diese Funktion codiert einen Base64 String wieder als Bilddatei
    function dataToImage(data) {
        return Buffer.from(data, 'binary').toString('base64');
    }

    function restrictedToString(restricted) {
        if(restricted) {
            return "Mit Anmeldung";
        } else {
            return "Ohne Anmeldung";
        }
    }

    function costsWithCurrency(costs) {
        return costs + " €";
    }

    return (
        <Card className="sonar-eventcard">
            <div className="sonar-eventcard_container">
                <div className="sonar-eventcard_media-div">
                    <CardMedia className="sonar-eventcard_media"
                               component="img"
                               alt="family"
                               image={image}
                    />
                    <div className="sonar-eventcard_header">
                        <CardActions className="sonar-eventcard_top-icons">
                            <div className="sonar-eventcard_top-icon">
                                <Button size="small"><ShareIcon/></Button>
                            </div>
                            <div className="sonar-eventcard_top-icon">
                                <Button size="small"><BookmarkIcon/></Button>
                            </div>
                            <div id="sonar-eventcard_top-icon-buffer"></div>
                            <div className="sonar-eventcard_top-details">
                                <Button size="small">Details</Button>
                            </div>
                        </CardActions>
                        <CardActions className="sonar-eventcard_tags">
                            <div className="sonar-eventcard_tag-icon sonar-eventcard_tag-icon-date">
                                <label>Heudde</label>
                            </div>
                            <div className="sonar-eventcard_tag-icon sonar-eventcard_tag-icon-registration">
                                <label>{restrictedToString(restricted)}</label>
                            </div>
                        </CardActions>
                    </div>
                </div>

                <div className="sonar-eventcard_content">

                    <CardContent id="sonar-eventcard_content_text-div">

                        <div className="sonar-eventcard_content_text">
                            <p className="sonar-eventcard_content_text-title">
                                {title}
                            </p>
                            <p className="sonar-eventcard_content_text-date">
                                Datum: {date}
                            </p>
                            <p className="sonar-eventcard_content_text-costs">
                                {costsWithCurrency(costs)}
                            </p>
                        </div>
                    </CardContent>
                </div>
            </div>
        </Card>
    );
}
