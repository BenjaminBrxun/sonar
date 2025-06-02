import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import ShareIcon from '@mui/icons-material/Share';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import Typography from '@mui/material/Typography';
import "./EventCardModule.scss";
import eventCardImage from '../../../assets/images/event-mocks/family.png';

export default function EventCardModule() {
    return (
        <Card className="sonar-eventcard">
            <div className="sonar-eventcard_container">

                <div className="sonar-eventcard_header">
                    <CardActions className="sonar-eventcard_top-icons">
                        <div className="sonar-eventcard_top-icon">
                            <Button size="small"><ShareIcon/></Button>
                        </div>
                        <div className="sonar-eventcard_top-icon">
                            <Button size="small"><BookmarkIcon/></Button>
                        </div>
                        <div className="sonar-eventcard_top-details">
                            <Button size="small">Details</Button>
                        </div>
                    </CardActions>
                </div>

                <div className="sonar-eventcard_content">

                    <CardContent>
                        <CardMedia className="sonar-eventcard_media"
                                   component="img"
                                   alt="family"
                                   height="340"
                                   image={eventCardImage}
                        />
                        <div className="sonar-eventcard_content_text">
                            <p className="sonar-eventcard_content_text-title">
                                Skateboard Workshop für Jugendliche
                            </p>
                            <p className="sonar-eventcard_content_text-date">
                                Datum: 03.04.2025 14:00-16:00 Uhr
                            </p>
                            <p className="sonar-eventcard_content_text-costs">
                                Kostenlos (Ab 6 Jahren)
                            </p>
                        </div>
                    </CardContent>
                </div>
            </div>
        </Card>
    );
}
