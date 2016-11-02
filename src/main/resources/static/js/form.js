var datalist = document.getElementById('categList'),
    input_group = datalist.parentElement,
    last_input = input_group.querySelector('.last-input'),
    
    addInput = function() {
        last_input.className = last_input.className.replace(/\blast-input\b/,'');;
        last_input.removeEventListener('input', addInput);
        last_input.name = 'categ';
        var input = document.createElement('input');
        input.className = last_input.className + " last-input";
        input.id = 'categories[]';
        input.setAttribute('list', 'categories');
        input_group.appendChild(input);
        last_input = input;
        last_input.addEventListener('input', addInput);
    };
    
last_input.addEventListener('input', addInput);