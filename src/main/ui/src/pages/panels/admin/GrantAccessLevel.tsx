import {Box, FormControlLabel, FormGroup, Radio, RadioGroup} from "@material-ui/core";
import {Link} from 'react-router-dom';
import React from "react";
import {useTranslation} from 'react-i18next'
import RoundedButton from '../../../components/RoundedButton'
import {useSnackbarQueue} from "../../snackbar";
import {refreshToken} from "../../../Services/userService";
import axios from "../../../Services/URL";
import store from "../../../redux/store";


export default function Checkboxes() {
    const {token} = store.getState()
    const {t} = useTranslation()
    const showError = useSnackbarQueue('error')
    const showSuccess = useSnackbarQueue('success')
    const [selectedAccessLevel, setSelectedAccessLevel] = React.useState("");

    const currentAccount = JSON.parse(sessionStorage.getItem("grantAccessLevelAccount") as string)

    const handleConfirm = async () => {
        if (!["Moderator", "Administrator"].includes(selectedAccessLevel)) return;

        const json = {
            accountLogin: currentAccount.login,
            accessLevel: selectedAccessLevel.toUpperCase(),
            accountVersion: currentAccount.version
        }

        await axios.put("account/grant-access-level",
            json,
            {
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    'Authorization': `Bearer ${token}`,
                    "If-Match": currentAccount.etag
                }
            }
        ).then(() => {
            showSuccess(t('success.accessLevelAssigned'));
            //refreshToken()
        }).catch(error => {
            const message = error.response.data
            showError(t(message))
        });
    }
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSelectedAccessLevel(event.target.value)
    };

    return (
        <div>
            <FormGroup row>
                <RadioGroup onChange={handleChange} value={selectedAccessLevel} row>

                    {
                        currentAccount!.accessLevels.includes("MODERATOR") ? null :
                            <FormControlLabel
                                value={"Moderator"}
                                control={<Radio/>}
                                label="Moderator"
                                checked={selectedAccessLevel === "Moderator"}
                            />
                    }
                    {
                        currentAccount!.accessLevels.includes("ADMINISTRATOR") ? null :
                            <FormControlLabel
                                value="Administrator"
                                control={<Radio/>}
                                label="Administrator"
                                checked={selectedAccessLevel === "Administrator"}
                            />
                    }

                </RadioGroup>
            </FormGroup>
            <Box style={{marginTop: 16}}>
                <RoundedButton onClick={handleConfirm} color="blue" style={{marginRight: 16}}>
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