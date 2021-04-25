import {Checkbox, FormControlLabel, FormGroup} from "@material-ui/core";
import React from "react";
import {useTranslation} from 'react-i18next'


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
            </div>
    )
}