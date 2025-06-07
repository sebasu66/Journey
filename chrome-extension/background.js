let ws;

function connect() {
  ws = new WebSocket('ws://localhost:3001');

  ws.onopen = () => {
    console.log('WebSocket connected');
  };

  ws.onclose = () => {
    setTimeout(connect, 1000);
  };

  ws.onmessage = (event) => {
    let msg;
    try {
      msg = JSON.parse(event.data);
    } catch {
      return;
    }
    if (msg.query && msg.id) {
      handleQuery(msg.id, msg.query);
    }
  };
}

function handleQuery(id, query) {
  chrome.tabs.query({ url: '*://chat.openai.com/*' }, (tabs) => {
    if (!tabs.length) {
      ws.send(JSON.stringify({ id, answer: 'No ChatGPT tab found' }));
      return;
    }
    const tabId = tabs[0].id;
    chrome.tabs.sendMessage(tabId, { type: 'ASK_GPT', text: query }, (res) => {
      if (chrome.runtime.lastError) {
        ws.send(JSON.stringify({ id, answer: chrome.runtime.lastError.message }));
      } else if (res && res.text) {
        ws.send(JSON.stringify({ id, answer: res.text }));
      } else {
        ws.send(JSON.stringify({ id, answer: '' }));
      }
    });
  });
}

connect();
