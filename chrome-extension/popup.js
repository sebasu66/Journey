document.getElementById('send').addEventListener('click', () => {
  const query = document.getElementById('query').value.trim();
  if (!query) return;

  chrome.tabs.query({ url: '*://chat.openai.com/*' }, (tabs) => {
    if (!tabs.length) {
      document.getElementById('response').textContent = 'No ChatGPT tab found.';
      return;
    }
    const tabId = tabs[0].id;
    chrome.tabs.sendMessage(tabId, { type: 'ASK_GPT', text: query }, (res) => {
      if (chrome.runtime.lastError) {
        document.getElementById('response').textContent = 'Error: ' + chrome.runtime.lastError.message;
      } else if (res && res.text) {
        document.getElementById('response').textContent = res.text;
      } else {
        document.getElementById('response').textContent = 'No response received.';
      }
    });
  });
});
