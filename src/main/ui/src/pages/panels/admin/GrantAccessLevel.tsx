import {Box, FormControlLabel, FormGroup, Radio, RadioGroup} from "@material-ui/core";
import {Link} from 'react-router-dom';
import React from "react";
import {useTranslation} from 'react-i18next'
import RoundedButton from '../../../components/RoundedButton'


export default function Checkboxes() {
    const {t} = useTranslation()
    const [selectedAccessLevel, setSelectedAccessLevel] = React.useState("");

    const currentAccount = JSON.parse(sessionStorage.getItem("grantAccessLevelAccount") as string)


    const handleConfirm = async () => {
        if (!["Moderator", "Administrator"].includes(selectedAccessLevel)) return;

        const json = {
            accountLogin: currentAccount.login,
            accessLevel: selectedAccessLevel.toUpperCase(),
            accountVersion: currentAccount.version
        }

        // await axios.put("http://localhost:8080/api/account/grant-access-level", {
        //     json,
        //     mode: 'no-cors',
        //     headers: {
        //         "Content-Type": "application/json",
        //         "Accept": "application/json",
        //         "If-Match": currentAccount.etag
        //     }
        // })

        /// above axios code results in java backend not seeing headers, most likely CORS issue,
        //  no-cors mode does not resolve it for axios, it works with fetch though

        await fetch("http://localhost:8080/api/account/grant-access-level", {
            method: "PUT",
            mode: "same-origin",
            body: JSON.stringify(json),
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "If-Match": currentAccount.etag
            }
        })
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