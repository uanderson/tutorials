import { newE2EPage } from '@stencil/core/testing';

describe('sb-emojitton', () => {
  it('renders', async () => {
    const page = await newE2EPage();
    await page.setContent('<sb-emojitton></sb-emojitton>');
    const element = await page.find('sb-emojitton');
    expect(element).not.toBeNull();
  });

  it('renders emoji', async () => {
    const page = await newE2EPage();
    await page.setContent('<sb-emojitton></sb-emojitton>');

    const element = await page.find('sb-emojitton');
    element.setProperty('emoji', '😀');
    element.setProperty('text', 'Smile');
    await page.waitForChanges();

    let emoji = await page.find('sb-emojitton >>> button :nth-child(1)');
    expect(emoji).toBeTruthy();
    expect(emoji).toHaveClasses(['emoji', 'prepend']);
    expect(emoji).toEqualText('😀');

    element.setProperty('position', 'append');
    await page.waitForChanges();

    expect(emoji).toBeTruthy();
    expect(emoji).toHaveClasses(['emoji', 'prepend']);
    expect(emoji).toEqualText('😀');
  });
});
