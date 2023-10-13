alter table restaurante_formas_pagamento
    add unique(restaurante_id, forma_pagamento_id);