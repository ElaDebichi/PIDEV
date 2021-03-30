<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210329221256 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE candidat_evenements (candidat_id INT NOT NULL, evenements_id INT NOT NULL, INDEX IDX_7928BBA98D0EB82 (candidat_id), INDEX IDX_7928BBA963C02CD4 (evenements_id), PRIMARY KEY(candidat_id, evenements_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE candidat_evenements ADD CONSTRAINT FK_7928BBA98D0EB82 FOREIGN KEY (candidat_id) REFERENCES user (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE candidat_evenements ADD CONSTRAINT FK_7928BBA963C02CD4 FOREIGN KEY (evenements_id) REFERENCES evenements (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE candidat_evenements');
    }
}
