function formatterCombobox(dict, value) {
    var color = 'black';
    var data = getDict(dict, value);
    if (data) {
        color = data.color;
        if (!color) {
            return data.name;
        }
        return '<font color="' + color + '">' + data.name + '</font>';
    }
    return value;
}

function getDict(dict, id) {
    var name;
    var obj;
    for (var i = 0; i < dict.length; i++) {
        obj = dict[i];
        if (obj.id == id) {
            return obj;
        }
    }
    return null;
}


dict_status = [
    {id: '0', name: '无效', color: 'red'},
    {id: '1', name: '有效'}
];

