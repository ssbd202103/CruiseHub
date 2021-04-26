import {
    TextField,
    InputAdornment
} from '@material-ui/core'
import {makeStyles} from '@material-ui/styles'
import * as React from 'react'

const useStyles = makeStyles(theme => ({
    root: {
        borderColor: 'var(--dark)',
        '& input:focus:focus + fieldset': {
            borderColor: 'var(--dark)'
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
        color: 'var(--dark)'
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
    return (<TextField
        type={type || "text"}
        className={classes.root + ' ' + (styles || "")}
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