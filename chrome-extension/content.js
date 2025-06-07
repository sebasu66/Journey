function sendGPTQuery(text, sendResponse) {
  const textarea = document.querySelector('textarea');
  if (!textarea) {
    sendResponse({ text: 'Textarea not found' });
    return;
  }
  textarea.focus();
  textarea.value = text;
  textarea.dispatchEvent(new Event('input', { bubbles: true }));
  const sendButton = document.querySelector('textarea + button');
  if (sendButton) {
    sendButton.click();
  } else {
    textarea.form.dispatchEvent(new KeyboardEvent('keydown', { key: 'Enter', bubbles: true }));
  }

  const observer = new MutationObserver((mutations, obs) => {
    const messages = document.querySelectorAll('main .markdown');
    if (messages.length) {
      const last = messages[messages.length - 1];
      const text = last.textContent.trim();
      if (text) {
        obs.disconnect();
        sendResponse({ text });
      }
    }
  });
  observer.observe(document.body, { childList: true, subtree: true });
}

chrome.runtime.onMessage.addListener((msg, sender, sendResponse) => {
  if (msg.type === 'ASK_GPT') {
    sendGPTQuery(msg.text, sendResponse);
    return true; // keep channel open for async response
  }
});
