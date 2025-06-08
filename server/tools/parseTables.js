import fs from 'fs';

const text = fs.readFileSync('Tome of Adventure Design.txt', 'utf8');

function escapeRegex(str) {
  return str.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

function parseTable(title) {
  const regex = new RegExp(`(?:^|\n)Table\\s+\\d+-\\d+:\\s*${escapeRegex(title)}`, 'i');
  const match = text.match(regex);
  if (!match) return [];
  const startIndex = match.index + match[0].length;
  const rest = text.slice(startIndex);
  const nextTableIndex = rest.search(/Table\\s+\\d+-\\d+:/);
  const section = nextTableIndex !== -1 ? rest.slice(0, nextTableIndex) : rest;
  const lines = section.split(/\r?\n/).map(l => l.trim());

  const entries = [];
  let current = [];
  for (const line of lines) {
    if (!line ||
        /^Die Roll/i.test(line) ||
        /^Cycle or Trigger/i.test(line) ||
        /^Event/i.test(line) ||
        /^Comments/i.test(line) ||
        /^Nature of/i.test(line)) {
      continue;
    }
    if (/^BOOK/i.test(line)) break;
    if (/^Table\s+\d+-\d+:/i.test(line)) break;
    if (/^\d{1,3}(?:-\d{1,3})?$/.test(line)) {
      if (current.length) {
        entries.push(current.join(' ').replace(/\s+/g, ' ').trim());
        current = [];
      }
      continue;
    }
    current.push(line);
  }
  if (current.length) {
    entries.push(current.join(' ').replace(/\s+/g, ' ').trim());
  }
  return entries;
}

const tables = {
  randomActs: parseTable('Random Acts'),
  timeCycles: parseTable('Time Cycles'),
};

fs.mkdirSync('server/data', { recursive: true });
for (const [name, entries] of Object.entries(tables)) {
  fs.writeFileSync(`server/data/${name}.json`, JSON.stringify(entries, null, 2));
  console.log(`Wrote server/data/${name}.json with ${entries.length} entries`);
}
