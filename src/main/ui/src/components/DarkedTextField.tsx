import { 
    TextField,
    InputAdornment
} from '@material-ui/core'
import { makeStyles } from '@material-ui/styles'
import * as React from 'react'
import {useSelector} from "react-redux";
import {selectColor} from "../redux/slices/colorSlice";

const useStyles = makeStyles(theme => ({
    light: {
        borderColor: 'var(--dark)',
        color: 'var(--dark)',
        '& input:focus:focus + fieldset': {
            borderColor: 'var(--dark)'
        },
        '& input': {
            color: 'var(--dark)'
        },
        '& input + fieldset': {
            borderColor: 'var(--dark)'
        },
        '&:hover input + fieldset': {
            borderColor: 'var(--dark)'
        },
        '& .MuiInputLabel-root': {
            color: 'var(--dark-light)'
        }
    },
    dark: {
        borderColor: 'var(--white)',
        color: 'var(--white)',
        '& input:focus:focus + fieldset': {
            borderColor: 'var(--white)'
        },
        '& input + fieldset': {
            borderColor: 'var(--white)'
        },
        '& input': {
            color: 'var(--white)'
        },
        '& input::placeholder': {
            color: 'var(--white)'
        },
        '& .MuiOutlinedInput-root:hover .MuiOutlinedInput-notchedOutline': {
            borderColor: 'var(--white)'
        },
        '& .MuiInputLabel-root': {
            color: 'var(--white-dark)'
        }
    }
}))

const inputStyle = {
    fontFamily: "'Montserrat Alternates', sans-serif",
    fontSize: '1.2rem',
}

const labelStyle = {
    style: {
        fontFamily: "'Montserrat', sans-serif",
        fontSize: '1.1rem',
    }
}

export interface DarkedTextFieldProps {
    readonly type?: "text" | "email" | "password",
    readonly className?: string,
    readonly icon?: JSX.Element,
    readonly placeholder?: string,
    readonly label?: string,
    readonly style?: React.CSSProperties
}

const DarkedTextField = React.forwardRef((props: DarkedTextFieldProps, ref) => {

        const classes = useStyles()

        const {
            className: styles,
            icon,
            type,
            placeholder,
            label,
            style,
        } = props

        const color = useSelector(selectColor)

        return (<TextField
            type={type || "text"}
            className={classes[color ? 'light' : 'dark'] + ' ' + (styles || "")}
            variant="outlined"
            label={label || ""}
            InputProps={{
                startAdornment: (
                    <InputAdornment position="start">
                        {icon || ""}
                    </InputAdornment>
                ),
                style: inputStyle,
            }}
            InputLabelProps={labelStyle}
            placeholder={placeholder || ""}
            style={style || undefined}
            ref={ref as React.RefObject<HTMLDivElement>}
        />)
    });

export default DarkedTextField;