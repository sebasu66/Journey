//load default style settings, can be overwriten by gamees by defining the same objects
//completely or partially
const textStyle = {
    "headerStyle":{
        "dropShadow": true,
        "dropShadowAlpha": 0.2,
        "dropShadowBlur": 7,
        "fontFamily": "Comic Sans MS",
        "fontWeight": "bolder",
        "padding": 3,
        "textBaseline": "middle"
    }
    };

const headerHeight = 100;
//function to return style object
function getTextStyle() {
    return textStyle;
    }

