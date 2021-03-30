<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210330144706 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE tutor ADD employer_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE tutor ADD CONSTRAINT FK_9907464841CD9E7A FOREIGN KEY (employer_id) REFERENCES user (id)');
        $this->addSql('CREATE INDEX IDX_9907464841CD9E7A ON tutor (employer_id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE tutor DROP FOREIGN KEY FK_9907464841CD9E7A');
        $this->addSql('DROP INDEX IDX_9907464841CD9E7A ON tutor');
        $this->addSql('ALTER TABLE tutor DROP employer_id');
    }
}
