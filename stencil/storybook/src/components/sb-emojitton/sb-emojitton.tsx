import { Component, h, Prop } from '@stencil/core';

@Component({
  tag: 'sb-emojitton',
  styleUrl: 'sb-emojitton.css',
  shadow: true
})
export class SbEmojitton {
  @Prop() position: 'prepend' | 'append' = 'prepend';
  @Prop() emoji: string;
  @Prop() text: string;

  canPrepend(): boolean {
    return this.position === 'prepend';
  }

  canAppend(): boolean {
    return this.position === 'append';
  }

  renderEmoji() {
    return <span class={'emoji ' + this.position}>{this.emoji}</span>;
  }

  render() {
    return (
      <button>
        {this.canPrepend() && this.renderEmoji()}
        {this.text}
        {this.canAppend() && this.renderEmoji()}
      </button>
    );
  }
}
