import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

import eventCardImage from '../../../assets/images/event-mocks/family.png';

export default function EventCardModule() {
    return (
        <Card sx={{ maxWidth: 345 }}>
            <div className="sonar-eventcard">

                <div className="sonar-eventcard_header">
                    <CardActions>
                        <Button size="small">Share</Button>
                        <Button size="small">Bookmark</Button>
                        <Button size="small">Details</Button>
                    </CardActions>
                    <CardMedia
                        component="img"
                        alt="family"
                        height="340"
                        image={eventCardImage}
                    />
                    <CardActions>
                        <Button size="small">Share</Button>
                        <Button size="small">Learn More</Button>
                    </CardActions>
                </div>

                <div className="sonar-eventcard_content">
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="div">
                            Lizard
                        </Typography>
                        <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                            Lizards are a widespread group of squamate reptiles, with over 6,000
                            species, ranging across all continents except Antarctica
                        </Typography>
                    </CardContent>
                </div>
            </div>
        </Card>
    );
}
