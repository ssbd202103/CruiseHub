import { 
    TextField,
    InputAdornment
} from '@material-ui/core'
import { withStyles } from '@material-ui/styles'
import * as React from 'react'

const StyledTextFiled = withStyles({
    root: {
        borderColor: 'var(--dark)',
        '& input:focus:focus + fieldset': {
            borderColor: 'var(--dark)'
        }
    }
})(TextField)

const inputStyle = {
    fontFamily: "'Montserrat Alternates', sans-serif",
    fontSize: '1.4rem',
}

const labelStyle = {
    style: {
        fontFamily: "'Montserrat', sans-serif",
        fontSize: '1.3rem',
        color: 'var(--dark)'
    }
}

export interface DarkedTextFieldProps {
    readonly type: "text" | "email" | "password",
    readonly className?: string,
    readonly icon: JSX.Element,
    readonly placeholder?: string,
    readonly label?: string
}

export default function DarkedTextField(props: any) {
    const {
        className: styles,
        icon,
        type,
        placeholder,
        label
    } = props

    return (
        <StyledTextFiled 
            type={type || "text"}      
            className={styles || ""} 
            variant="outlined"
            label={label || ""}
            InputProps={{
                startAdornment: (
                    <InputAdornment position="start">
                        {icon}
                    </InputAdornment>
                ),
                style: inputStyle,
            }}
            InputLabelProps={labelStyle}
            placeholder={placeholder || ""}
        />
    )
}