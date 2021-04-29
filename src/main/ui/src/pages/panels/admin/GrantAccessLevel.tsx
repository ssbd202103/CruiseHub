import {Checkbox, FormControlLabel, FormGroup, Box} from "@material-ui/core";
import {Link} from 'react-router-dom';
import React from "react";
import {useTranslation} from 'react-i18next'
import RoundedButton from '../../../components/RoundedButton'


export default function Checkboxes() {
        const {t} = useTranslation()
        const [state, setState] = React.useState({
            checkedA: true,
            checkedB: false,
            checkedC: false,
            checkedD: false,

        });

        const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
            setState({ ...state, [event.target.name]: event.target.checked });
        };

        return (
            <div>
                <FormGroup row>
                    <FormControlLabel
                        control={<Checkbox checked={state.checkedA} onChange={handleChange} name="checkedA" />}
                        label="Client"
                    />
                    <FormControlLabel
                        control={<Checkbox checked={state.checkedB} onChange={handleChange} name="checkedB" />}
                        label="Business Worker"
                    />
                    <FormControlLabel
                        control={<Checkbox checked={state.checkedC} onChange={handleChange} name="checkedC" />}
                        label="Moderator"
                    />
                    <FormControlLabel
                        control={<Checkbox checked={state.checkedD} onChange={handleChange} name="checkedD" />}
                        label="Administrator"
                    />

                </FormGroup>
                <Box style={{marginTop: 16}}>
                    <RoundedButton color="blue" style={{marginRight: 16}}>
                        {t("confirm")}
                    </RoundedButton>
                    <Link to="/panels/adminPanel/accounts">
                        <RoundedButton color="pink">
                            {t("go back")}
                        </RoundedButton>
                    </Link>
                </Box>
            </div>
    )
}