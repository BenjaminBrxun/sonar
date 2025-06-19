import React from "react";
import "./DetailComponent.scss"
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import ShareIcon from '@mui/icons-material/Share';
import CancelIcon from '@mui/icons-material/Cancel';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import image from "../../../assets/images/event-mocks/gaming.png"
import map from "../../../assets/images/map_dark.png"

const title = "Gaming Night im Jugendzentrum";
const date = "09.01.2025 ab 18:00 Uhr"
const costs = "3€ (Ab 12 Jahren)"
const locate = "Stadtpark Herne"
const address = "Musterstraße 1, 44623 Herne"
const headline = "Seid dabei, wenn das Jugendzentrum in Herne zur Gaming-Arena wird!"
const text = "Auf mehreren Konsolen können Jugendliche und junge Erwachsene ihr Können in beliebten Spielen wie Mario Kart\", FIFA\" und „Fortnite\" unter Beweis stellen.\n\nNeben spannenden Turnieren gibt es Retro-Gaming-Ecken und Virtual-Reality-Stationen für echte Highlights. Snacks und Getränke stehen bereit, um euch durch die Nacht zu bringen. Holt euch die Controller und zeigt, wer der Boss ist!"


export function DetailComponent() { //eigentlich {title, date, costs, image} hier als Argument
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
                            <div className="sonar-eventcard_top-cancel">
                                <Button><CancelIcon/></Button>
                            </div>
                        </CardActions>
                        <CardActions className="sonar-eventcard_tags">
                            <div className="sonar-eventcard_tag-icon sonar-eventcard_tag-icon-date">
                                <label>Heudde</label>
                            </div>
                            <div className="sonar-eventcard_tag-icon sonar-eventcard_tag-icon-registration">
                                <label>Ausjebuucht</label>
                            </div>
                        </CardActions>
                    </div>
                </div>

                <div className="sonar-eventcard_content">

                    <CardContent id="sonar-eventcard_content_text-div">

                        <div className="sonar-eventcard_content_text">
                            <p className="sonar-eventcard_content_text-date">
                                Datum: {date}
                            </p>
                            <p className="sonar-eventcard_content_text-costs">
                                Preis: {costs}
                            </p>
                            <p className="sonar-eventcard_content_text-title">
                                {title}
                            </p>
                            <p className="sonar-eventcard_content_text-headline">
                                {headline}
                            </p>
                            <p className="sonar-eventcard_content_text-text">
                                {text}
                            </p>
                            <CardActions className="sonar-eventcard_categorys">
                                <div className="sonar-eventcard_category">
                                    <label>Gaming</label>
                                </div>
                                <div className="sonar-eventcard_category">
                                    <label>Computer</label>
                                </div>
                            </CardActions>
                            <CardMedia className="sonar-eventcard_media"
                                       component="img"
                                       alt="family"
                                       image={map}
                            />
                            <p className="sonar-eventcard_content_text-location">
                                Adresse<br/>{locate}<br/>{address}
                            </p>
                            <div className="sonar-eventcard_content_text-button">
                                <button>Route auf Google Maps</button>
                            </div>
                        </div>
                    </CardContent>
                </div>
            </div>
        </Card>
    )

}